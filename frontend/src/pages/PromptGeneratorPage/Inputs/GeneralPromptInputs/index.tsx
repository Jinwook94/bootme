import React from 'react';
import QuestionInput from '../../InputModules/QuestionInput';
import ExampleInput from '../../InputModules/ExampleInput';
import TechStackInput from '../../InputModules/TechStackInput';
import ResponseFormatInput from '../../InputModules/ResponseFormatInput';
import PromptLanguageInput from '../../InputModules/PromptLanguageInput';
import PrincipleInput from '../../InputModules/PrincipleInput';

const GeneralPromptInputs = () => {
  return (
    <>
      <QuestionInput />
      <ExampleInput />
      <TechStackInput />
      <PrincipleInput />
      <ResponseFormatInput />
      <PromptLanguageInput />
    </>
  );
};

export default GeneralPromptInputs;
