import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ReactGA from 'react-ga4';
import { useSecret } from '../hooks/useSecret';

/**
 * URI 변경 추적 컴포넌트
 * URI 변경시 pageview 이벤트 전송
 */
const RouteChangeTracker: React.FC = () => {
  const location = useLocation();
  const [initialized, setInitialized] = useState<boolean>(false);
  const { secrets } = useSecret();
  const GA_MEASUREMENT_ID = secrets?.['gaMeasurementId'];

  useEffect(() => {
    if (typeof GA_MEASUREMENT_ID === 'string') {
      ReactGA.initialize(GA_MEASUREMENT_ID);
      setInitialized(true);
    }
  }, []);

  useEffect(() => {
    if (initialized) {
      ReactGA.set({ page: location.pathname });
      ReactGA.send({ hitType: 'pageview', page: location.pathname });
    }
  }, [initialized, location]);

  return null;
};

export default RouteChangeTracker;
