import { useQuery, UseQueryOptions } from 'react-query';
import { AxiosError, AxiosResponse } from 'axios';
import { courseKeys } from '../queryKeys';
import { fetcher } from '../../../api/fetcher';

const useCourses = ({
  filters,
  options,
}: {
  filters?: string;
  options?: UseQueryOptions<
    AxiosResponse<Course[]>,
    AxiosError,
    Course[],
    (readonly ['courses', 'list', { readonly filters: string | undefined }])[]
  >;
}) =>
  useQuery([courseKeys.list(filters)], () => fetcher.get(`/admin/courses`), {
    select: data => data.data,
    refetchOnWindowFocus: false, // disable automatic refetch on window focus
    refetchInterval: 24 * 60 * 60 * 1000, // refetch every 24 hours
    staleTime: 24 * 60 * 60 * 1000, // consider data fresh for 24 hours
    cacheTime: 23 * 60 * 60 * 1000, // maximum time to cache data (23 hours)
    keepPreviousData: true, // keep previous data while fetching new data
    enabled: true, // todo: 조건부로 true 설정되도록 수정 필요함 (현재는 페이지 refresh 하면 refetch)
    ...options,
  });

export default useCourses;
