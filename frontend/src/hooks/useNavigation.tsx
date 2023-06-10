import { useLocation, useNavigate } from 'react-router-dom';
import React, { createContext, useContext, useEffect, useState } from 'react';
import PATH from '../constants/path';

const NavigationContext = createContext<NavigationContextProps>({
  goToPage: () => undefined,
  goBack: () => undefined,
  historyStack: [],
});

export const NavigationProvider = ({ children }: { children: React.ReactNode }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const [historyStack, setHistoryStack] = useState<string[]>([]);
  const [previousUrl, setPreviousUrl] = useState<string | null>(null);

  const goToPage = (path: string) => {
    setHistoryStack(prev => [...prev, location.pathname]);
    window.location.href = path;
  };

  const goBack = () => {
    if (historyStack.length > 1) {
      const newHistory = [...historyStack];
      newHistory.pop();
      setHistoryStack(newHistory);
      navigate(newHistory[newHistory.length - 1]);
    } else {
      navigate(PATH.HOME);
    }
  };

  useEffect(() => {
    if (location.pathname !== previousUrl) {
      setHistoryStack(prev => [...prev, location.pathname]);
    }
    setPreviousUrl(location.pathname);
  }, [location.pathname]);

  useEffect(() => {
    const storedHistory = localStorage.getItem('historyStack');
    if (storedHistory) {
      setHistoryStack(JSON.parse(storedHistory));
    } else {
      setHistoryStack([location.pathname]);
    }
  }, []);

  useEffect(() => {
    localStorage.setItem('historyStack', JSON.stringify(historyStack));
  }, [historyStack]);

  return <NavigationContext.Provider value={{ goToPage, goBack, historyStack }}>{children}</NavigationContext.Provider>;
};

export const useNavigation = () => useContext(NavigationContext);

interface NavigationContextProps {
  goToPage: (path: string) => void;
  goBack: () => void;
  historyStack: string[];
}
