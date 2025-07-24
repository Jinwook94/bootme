import React, { createContext, useCallback, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import * as jose from 'jose';

const SecretContext = createContext<SecretContextProps>({
  secrets: undefined,
  fetchSecrets: async () => ({} as SecretsResponse),
});

export const SecretProvider = ({ children }: SecretProviderProps) => {
  const [secrets, setSecrets] = useState<SecretsResponse>();

  const fetchSecrets = useCallback(async () => {
    const jwt = await generateJwt();
    const response = await fetcher.get<SecretsResponse>('auth/secrets', {
      headers: {
        'Bootme-Secret': 'Bearer ' + jwt,
      },
    });
    setSecrets(response.data);
    return response.data;
  }, []);

  return (
    <SecretContext.Provider
      value={{
        secrets,
        fetchSecrets,
      }}
    >
      {children}
    </SecretContext.Provider>
  );

  function generateJwt() {
    const alg = 'HS256';
    const typ = 'JWT';

    // workflow 빌드 중 깃허브 리포지토리 시크릿으로 교체 (frontend-deploy.yml)
    const issuer = 'bootme.co.kr';
    const audience = 'bootme.co.kr';
    const signingKey = 'dRgUkXp2s5v8yBEHMbPeShVmYq3t6w9zCFJNcRfTjWnZr4u7xADG'; // todo AWS Secretes Manager 등 사용하여 개선
    const encodedKey = new TextEncoder().encode(signingKey);

    return new jose.SignJWT({})
      .setProtectedHeader({ alg, typ })
      .setIssuedAt()
      .setExpirationTime('5m')
      .setIssuer(issuer)
      .setAudience(audience)
      .sign(encodedKey);
  }
};

export const useSecret = () => useContext(SecretContext);

interface SecretContextProps {
  secrets: SecretsResponse | undefined;
  fetchSecrets: () => Promise<SecretsResponse>;
}

interface SecretProviderProps {
  children: React.ReactNode;
}

interface SecretsResponse {
  apiUrl: string;
  googleClientId: string;
  googleAudience: string;
  naverClientId: string;
  naverClientSecret: string;
  naverAudience: string;
  naverSigningKey: string;
  kakaoRestApiKey: string;
  kakaoClientSecret: string;
  kakaoAudience: string;
  kakaoJavascriptKey: string;
  bootmeAudience: string;
  bootmeSigningKey: string;
  gaMeasurementId: string;
}
