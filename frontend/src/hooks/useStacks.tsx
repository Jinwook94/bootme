import { fetcher } from '../api/fetcher';
import React, { createContext, useContext, useState } from 'react';

const StackContext = createContext<StackContextProps>({
  loadingStackList: false,
  stackList: [],
  icons: {},
  fetchStacks: async () => [],
});

export const StackProvider = ({ children }: { children: React.ReactNode }) => {
  const [loadingStackList, setLoadingStackList] = useState(true);
  const [stackList, setStackList] = useState<SelectItem[]>([]);
  const [icons, setIcons] = useState<{ [key: string]: string }>({});

  async function fetchStacks() {
    setLoadingStackList(true);
    const response = await fetcher.get('/stacks');
    const responseData: StackResponse[] = response.data;

    const mappedResponse: SelectItem[] = responseData.map(stack => ({
      ...stack,
      value: stack.name,
      label: stack.name,
      group: stack.type.toUpperCase(),
    }));

    const mappedIcons: { [key: string]: string } = responseData.reduce((acc: { [key: string]: string }, stack) => {
      acc[stack.name] = stack.icon;
      return acc;
    }, {});

    setStackList(mappedResponse);
    setIcons(mappedIcons);
    setLoadingStackList(false);
    return stackList;
  }

  return (
    <StackContext.Provider
      value={{
        loadingStackList,
        stackList,
        icons,
        fetchStacks,
      }}
    >
      {children}
    </StackContext.Provider>
  );
};

export const useStacks = () => useContext(StackContext);

interface StackContextProps {
  loadingStackList: boolean;
  stackList: SelectItem[];
  icons: { [key: string]: string };
  fetchStacks: () => Promise<SelectItem[]>;
}

interface StackResponse {
  name: string;
  type: string;
  icon: string;
}

interface SelectItem extends StackResponse {
  value: string;
  label: string;
  group: string;
}
