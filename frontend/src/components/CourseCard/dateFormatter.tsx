import React from 'react';

const DateFormatter = ({ date, korean = false }: DateFormatterProps) => {
  if (date) {
    const dateObject = new Date(date);

    let formattedDate: string;
    if (korean) {
      formattedDate = dateObject.toLocaleDateString('ko-KR', { month: 'long', day: '2-digit' });
      formattedDate = formattedDate.replace(' 0', ' ');
    } else {
      formattedDate = dateObject.toLocaleDateString('en-US', { month: '2-digit', day: '2-digit' });
      formattedDate = formattedDate.replace('/', '-');
    }

    return <span>{formattedDate}</span>;
  }

  return null;
};

export default DateFormatter;

interface DateFormatterProps {
  date: string | undefined;
  korean?: boolean;
}
