import {ButtonStyle} from './style';
import { ComponentMeta, ComponentStory } from '@storybook/react';

export default {
  title: 'Button',
  component: ButtonStyle,
} as ComponentMeta<typeof ButtonStyle>;

const Template: ComponentStory<typeof ButtonStyle> = (args) => <ButtonStyle {...args}></ButtonStyle>;

export const Base = Template.bind({});
Base.args = {
  children: '기본버튼',
};

export const Submit = Template.bind({});
Submit.args = {
  children: '제출하기',
};
