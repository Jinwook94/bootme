import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useSnackbar } from './useSnackbar';
import SNACKBAR_MESSAGE, { CHECK, EXCLAMATION } from '../constants/snackbar';

export enum LoadingState {
  None = 'None',
  Profile = 'Profile',
  MyProfile = 'MyProfile',
  UpdateProfile = 'UpdateProfile',
  UpdateProfileImage = 'UpdateProfileImage',
}

const ProfileContext = createContext<ProfileContextProps>({
  loadingState: LoadingState.None,
  profileImage: '',
  email: '',
  setEmail: () => {},
  nickname: '',
  setNickname: () => {},
  job: '',
  setJob: () => {},
  stacks: [],
  setStacks: () => {},
  stackNames: [],
  setStackNames: () => {},
  isNicknameChanged: false,
  isNicknameDuplicate: false,
  setIsNicknameDuplicate: () => {},
  nicknameCheckDone: false,
  setNicknameCheckDone: () => {},
  fetchProfile: async () => ({
    profileImage: '',
    email: '',
    nickname: '',
    job: '',
    stacks: [],
  }),
  fetchMyProfile: async () => ({
    profileImage: '',
    email: '',
    nickname: '',
    job: '',
    stacks: [],
  }),
  fetchNicknameDuplicate: async () => {},
  updateProfileImage: async () => {},
  updateMemberProfile: async () => {},
  handleSubmit: () => {},
  handleNicknameChange: () => {},
  handleJobChange: () => {},
});

export const ProfileProvider = ({ children }: { children: React.ReactNode }) => {
  const { showSnackbar } = useSnackbar();
  const [loadingState, setLoadingState] = useState<LoadingState>(LoadingState.None);
  const [profileImage, setProfileImage] = useState(localStorage.getItem('profileImage') || '');
  const [email, setEmail] = useState(localStorage.getItem('email') || '');
  const [nickname, setNickname] = useState(localStorage.getItem('nickname') || '');
  const [job, setJob] = useState(localStorage.getItem('job') || '');
  const [stacks, setStacks] = useState<Stack[]>([]);
  const [stackNames, setStackNames] = useState<string[]>([]);
  const [isNicknameChanged, setIsNicknameChanged] = useState(false);
  const [isNicknameDuplicate, setIsNicknameDuplicate] = useState(false);
  const [nicknameCheckDone, setNicknameCheckDone] = useState(false);

  const fetchProfile = async (memberId: number | string) => {
    setLoadingState(LoadingState.Profile);
    const { data } = await fetcher.get<ProfileResponse>(`member/${memberId}/profile`);
    const { profileImage, email, nickname, job, stacks } = data;
    setProfileImage(profileImage);
    setEmail(email);
    setNickname(nickname);
    setJob(job || '');
    setStacks(stacks);

    setLoadingState(LoadingState.None);
    return data;
  };

  const fetchMyProfile = async () => {
    setLoadingState(LoadingState.MyProfile);
    const { data } = await fetcher.get<MyProfileResponse>('member/me');
    const { profileImage, email, nickname, job, stacks } = data;
    setProfileImage(profileImage);
    setEmail(email);
    setNickname(nickname);
    setJob(job || '');
    setStackNames(stacks);

    setLoadingState(LoadingState.None);
    return data;
  };

  const fetchNicknameDuplicate = async () => {
    fetcher.get<boolean>(`member/nickname/${nickname}/exists`).then(r => {
      setIsNicknameDuplicate(r.data);
      setNicknameCheckDone(true);
      setIsNicknameChanged(false);
    });
  };

  const memberId = localStorage.getItem('memberId');

  const updateProfileImage = async (profileImage: string) => {
    try {
      setLoadingState(LoadingState.UpdateProfileImage);
      await fetcher.patch(`member/${memberId}/profile_image`, { profileImage });
      localStorage.setItem('profileImage', profileImage);
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_PROFILE_IMAGE_UPDATE, CHECK);
      setLoadingState(LoadingState.None);
    } catch (e: any) {
      if (e.response && e.response.data && e.response.data.message) {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_PROFILE_IMAGE_UPDATE + ': ' + e.response.data.message, EXCLAMATION);
        setLoadingState(LoadingState.None);
      } else {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_PROFILE_IMAGE_UPDATE, EXCLAMATION);
        setLoadingState(LoadingState.None);
      }
    }
  };

  const updateMemberProfile = async (data: UpdateProfileRequest) => {
    try {
      setLoadingState(LoadingState.UpdateProfile);
      await fetcher.put(`member/${memberId}`, data);
      const { nickname, job } = data;
      localStorage.setItem('nickname', nickname);
      if (typeof job === 'string') {
        localStorage.setItem('job', job);
      }
      setLoadingState(LoadingState.None);
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_PROFILE_UPDATE, CHECK);
    } catch (e: any) {
      if (e.response && e.response.data && e.response.data.message) {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_PROFILE_UPDATE + ': ' + e.response.data.message, EXCLAMATION);
        setLoadingState(LoadingState.None);
      } else {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_PROFILE_UPDATE, EXCLAMATION);
        setLoadingState(LoadingState.None);
      }
    }
  };

  const handleSubmit = (values: any) => {
    if (isNicknameChanged && !nicknameCheckDone) {
      showSnackbar(SNACKBAR_MESSAGE.DUPLICATION_CHECK_NEEDED, EXCLAMATION);
      return;
    }
    updateMemberProfile({ ...values, stackNames }).catch();
  };

  const handleNicknameChange = (event: { target: { value: string } }) => {
    setNickname(event.target.value);
    setIsNicknameChanged(true);
    setNicknameCheckDone(false);
  };

  const handleJobChange = (event: { target: { value: string } }) => {
    setJob(event.target.value);
  };

  return (
    <ProfileContext.Provider
      value={{
        loadingState,
        profileImage,
        email,
        setEmail,
        nickname,
        setNickname,
        job,
        setJob,
        stacks,
        setStacks,
        stackNames,
        setStackNames,
        isNicknameChanged,
        isNicknameDuplicate,
        setIsNicknameDuplicate,
        nicknameCheckDone,
        setNicknameCheckDone,
        fetchProfile,
        fetchMyProfile,
        fetchNicknameDuplicate,
        updateProfileImage,
        updateMemberProfile,
        handleSubmit,
        handleNicknameChange,
        handleJobChange,
      }}
    >
      {children}
    </ProfileContext.Provider>
  );
};

export const useProfile = () => useContext(ProfileContext);

interface ProfileContextProps {
  loadingState: LoadingState;
  profileImage: string;
  email: string;
  setEmail: React.Dispatch<React.SetStateAction<string>>;
  nickname: string;
  setNickname: React.Dispatch<React.SetStateAction<string>>;
  job: string;
  setJob: React.Dispatch<React.SetStateAction<string>>;
  stacks: Stack[];
  setStacks: React.Dispatch<React.SetStateAction<Stack[]>>;
  stackNames: string[];
  setStackNames: React.Dispatch<React.SetStateAction<string[]>>;
  isNicknameChanged: boolean;
  isNicknameDuplicate: boolean;
  setIsNicknameDuplicate: React.Dispatch<React.SetStateAction<boolean>>;
  nicknameCheckDone: boolean;
  setNicknameCheckDone: React.Dispatch<React.SetStateAction<boolean>>;
  fetchProfile: (memberId: number | string) => Promise<ProfileResponse>;
  fetchMyProfile: () => Promise<MyProfileResponse>;
  fetchNicknameDuplicate: () => Promise<void>;
  updateProfileImage: (imageUrl: string) => void;
  updateMemberProfile: (data: UpdateProfileRequest) => Promise<void>;
  handleSubmit: (values: any) => void;
  handleNicknameChange: (event: { target: { value: string } }) => void;
  handleJobChange: (event: { target: { value: string } }) => void;
}

interface Stack {
  name: string;
  type: string;
  icon: string;
}

interface ProfileResponse {
  profileImage: string;
  email: string;
  nickname: string;
  job: string;
  stacks: Stack[];
}

interface MyProfileResponse {
  profileImage: string;
  email: string;
  nickname: string;
  job: string;
  stacks: string[];
}

interface UpdateProfileRequest {
  email: string;
  nickname: string;
  job: string | null;
  stacks: string[];
}
