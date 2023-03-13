import React, { createContext, useContext, useEffect, useState } from 'react';
import { GetSecretValueCommand, SecretsManagerClient } from '@aws-sdk/client-secrets-manager';

const SecretContext = createContext<SecretContextProps>({
  secrets: {},
});

export const SecretProvider = ({ children }: SecretProviderProps) => {
  const [secrets, setSecrets] = useState<{ [key: string]: string }>({});
  const [secretName] = useState<string>('prod/React');
  const [accessKey] = useState<string>('AWS_SDK_CREDENTIAL_ACCESS_KEY');
  const [secretKey] = useState<string>('AWS_SDK_CREDENTIAL_SECRET_KEY');

  /**
   * aws-sdk 자격 증명 IAM 사용자: aws_sdk_credentials_client-secrets-manager
   *  해당 IAM 사용자의 액세스 키, 시크릿 키를 통해 aws-sdk 자격 증명 진행
   */
  const client = new SecretsManagerClient({
    region: 'ap-northeast-2',
    credentials: {
      accessKeyId: accessKey,
      secretAccessKey: secretKey,
    },
  });

  /**
   * AWS Secrets Manager 에 key-value 로 저장된 시크릿들을 가져와 객체에 저장
   */
  const saveAwsSecretsToObject = async () => {
    try {
      const response = await client.send(
        new GetSecretValueCommand({
          SecretId: secretName,
          VersionStage: 'AWSCURRENT',
        })
      );
      const secrets = JSON.parse(response.SecretString as string);
      setSecrets(secrets);
    } catch (error) {
      // For a list of exceptions thrown, see
      // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
      console.log(error);
    }
  };

  useEffect(() => {
    saveAwsSecretsToObject().catch(error => console.log(error));
  }, []);

  return (
    <SecretContext.Provider
      value={{
        secrets,
      }}
    >
      {children}
    </SecretContext.Provider>
  );
};

export const useSecret = () => useContext(SecretContext);

interface SecretContextProps {
  secrets: { [key: string]: string };
}

interface SecretProviderProps {
  children: React.ReactNode;
}
