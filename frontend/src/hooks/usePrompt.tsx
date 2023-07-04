import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';

const noop = () => {};
const noopAsync = async () => {};

const PromptContext = createContext<PromptContextProps>({
  isGenerating: false,
  isPromptGenerated: false,
  isSubmitting: false,
  isMessaging: false,
  category: '',
  setCategory: noop,
  question: '',
  setQuestion: noop,
  featureDescription: '',
  setFeatureDescription: noop,
  example: '',
  setExample: noop,
  exampleCode: '',
  setExampleCode: noop,
  techStack: [],
  setTechStack: noop,
  principles: [],
  setPrinciples: noop,
  apiDescription: '',
  setApiDescription: noop,
  erd: '',
  setErd: noop,
  entity: '',
  setEntity: noop,
  responseFormat: '',
  setResponseFormat: noop,
  promptLanguage: '',
  setPromptLanguage: noop,
  prompt: '',
  setPrompt: noop,
  gptResponse: '',
  setGptResponse: noop,
  sendPromptSubmissionRequest: noopAsync,
  submitGeneralPrompt: noopAsync,
  submitFeaturePrompt: noopAsync,
  submitApiDesignPrompt: noopAsync,
  sendMessage: async (): Promise<string> => '',
});

export const PromptProvider = ({ children }: { children: React.ReactNode }) => {
  const [isGenerating, setIsGenerating] = useState(false);
  const [isPromptGenerated, setIsPromptGenerated] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [isMessaging, setIsMessaging] = useState(false);
  const [category, setCategory] = useState<string>('general');
  const [question, setQuestion] = useState('');
  const [featureDescription, setFeatureDescription] = useState('');
  const [example, setExample] = useState('');
  const [exampleCode, setExampleCode] = useState('');
  const [techStack, setTechStack] = useState<string[]>([]);
  const [principles, setPrinciples] = useState<string[]>([]);
  const [apiDescription, setApiDescription] = useState<string>('');
  const [erd, setErd] = useState<string>('');
  const [entity, setEntity] = useState<string>('');
  const [responseFormat, setResponseFormat] = useState<string>('default');
  const [promptLanguage, setPromptLanguage] = useState<string>('english');
  const [prompt, setPrompt] = useState('');
  const [gptResponse, setGptResponse] = useState('');

  const sendPromptGenerationRequest = async (promptType: string, promptData: PromptData) => {
    setIsGenerating(true);
    try {
      const response = await fetcher.post(`/prompts/generation/${promptType}`, promptData);
      if (response.data && response.data.generatedPrompt) {
        setPrompt(response.data.generatedPrompt);
        setIsPromptGenerated(true);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setIsGenerating(false);
    }
  };

  const sendPromptSubmissionRequest = async () => {
    setIsSubmitting(true);
    try {
      const response = await fetcher.post(`/prompts/submission`, JSON.stringify({ prompt }), {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      if (response.data && response.data.gptResponse) {
        setGptResponse(response.data.gptResponse);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setIsSubmitting(false);
    }
  };

  const sendMessage = async (message: string) => {
    setIsMessaging(true);
    try {
      const response = await fetcher.post(`/gpt`, JSON.stringify({ message }), {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      if (response.data && response.data.message) {
        setGptResponse(response.data.message);
        return response.data.message;
      }
    } catch (err) {
      console.error(err);
    } finally {
      setIsMessaging(false);
    }
    return '';
  };

  const submitGeneralPrompt = async () => {
    const promptData = {
      promptType: 'general',
      question,
      example,
      techStack,
      principles,
      responseFormat,
      promptLanguage,
    };
    await sendPromptGenerationRequest('general', promptData);
  };

  const submitFeaturePrompt = async () => {
    const promptData = {
      promptType: 'feature',
      featureDescription,
      exampleCode,
      techStack,
      principles,
      responseFormat,
      promptLanguage,
    };

    await sendPromptGenerationRequest('feature', promptData);
  };

  const submitApiDesignPrompt = async () => {
    const promptData = {
      promptType: 'apiDesign',
      apiDescription,
      erd,
      entity,
      responseFormat,
      promptLanguage,
    };

    await sendPromptGenerationRequest('apiDesign', promptData);
  };

  return (
    <PromptContext.Provider
      value={{
        isGenerating,
        isPromptGenerated,
        isSubmitting,
        isMessaging,
        category,
        setCategory,
        question,
        setQuestion,
        featureDescription,
        setFeatureDescription,
        example,
        setExample,
        exampleCode,
        setExampleCode,
        techStack,
        setTechStack,
        principles,
        setPrinciples,
        apiDescription,
        setApiDescription,
        erd,
        setErd,
        entity,
        setEntity,
        responseFormat,
        setResponseFormat,
        promptLanguage,
        setPromptLanguage,
        prompt,
        setPrompt,
        gptResponse,
        setGptResponse,
        sendPromptSubmissionRequest,
        submitGeneralPrompt,
        submitFeaturePrompt,
        submitApiDesignPrompt,
        sendMessage,
      }}
    >
      {children}
    </PromptContext.Provider>
  );
};

export const usePrompt = () => useContext(PromptContext);

interface PromptContextProps {
  isGenerating: boolean;
  isPromptGenerated: boolean;
  isSubmitting: boolean;
  isMessaging: boolean;
  category: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
  question: string;
  setQuestion: React.Dispatch<React.SetStateAction<string>>;
  featureDescription: string;
  setFeatureDescription: React.Dispatch<React.SetStateAction<string>>;
  example: string;
  setExample: React.Dispatch<React.SetStateAction<string>>;
  exampleCode: string;
  setExampleCode: React.Dispatch<React.SetStateAction<string>>;
  techStack: string[];
  setTechStack: React.Dispatch<React.SetStateAction<string[]>>;
  principles: string[];
  setPrinciples: React.Dispatch<React.SetStateAction<string[]>>;
  apiDescription: string;
  setApiDescription: React.Dispatch<React.SetStateAction<string>>;
  erd: string;
  setErd: React.Dispatch<React.SetStateAction<string>>;
  entity: string;
  setEntity: React.Dispatch<React.SetStateAction<string>>;
  responseFormat: string;
  setResponseFormat: React.Dispatch<React.SetStateAction<string>>;
  promptLanguage: string;
  setPromptLanguage: React.Dispatch<React.SetStateAction<string>>;
  prompt: string;
  setPrompt: React.Dispatch<React.SetStateAction<string>>;
  gptResponse: string;
  setGptResponse: React.Dispatch<React.SetStateAction<string>>;
  sendPromptSubmissionRequest: () => Promise<void>;
  submitGeneralPrompt: () => Promise<void>;
  submitFeaturePrompt: () => Promise<void>;
  submitApiDesignPrompt: () => Promise<void>;
  sendMessage: (message: string) => Promise<string>;
}

type PromptData = {
  [key: string]: string | string[];
};
