import React from 'react';
import FeatureInput from '../../InputModules/FeatureInput';
import ExampleCodeInput from '../../InputModules/ExampleCodeInput';
import TechStackInput from '../../InputModules/TechStackInput';
import PrincipleInput from '../../InputModules/PrincipleInput';
import ResponseFormatInput from '../../InputModules/ResponseFormatInput';
import PromptLanguageInput from '../../InputModules/PromptLanguageInput';

const FeatureImplementPromptInputs = () => {
  return (
    <>
      <FeatureInput />
      <ExampleCodeInput />
      <TechStackInput />
      <PrincipleInput />
      <ResponseFormatInput />
      <PromptLanguageInput />
    </>
  );
};

export default FeatureImplementPromptInputs;
