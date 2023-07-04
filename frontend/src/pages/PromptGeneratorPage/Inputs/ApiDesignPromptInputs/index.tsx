import ErdInput from '../../InputModules/ErdInput';
import EntityInput from '../../InputModules/EntityInput';
import ResponseFormatInput from '../../InputModules/ResponseFormatInput';
import PromptLanguageInput from '../../InputModules/PromptLanguageInput';
import React from 'react';
import ApiDescriptionInput from '../../InputModules/ApiDescriptionInput';

// todo: JSON SPEC 멀티 셀렉트 추가?
const ApiDesignPromptInputs = () => {
  return (
    <>
      <ApiDescriptionInput />
      <ErdInput />
      <EntityInput />
      <ResponseFormatInput />
      <PromptLanguageInput />
    </>
  );
};

export default ApiDesignPromptInputs;
