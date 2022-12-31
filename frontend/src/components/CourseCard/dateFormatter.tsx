import React from 'react';

const DateFormatter = ({ date }: { date: string }) => {
  const dateObject = new Date(date);
  const formattedDate = dateObject.toLocaleDateString('en-US', { month: '2-digit', day: '2-digit' });
  const result = formattedDate.replace('/', '-');

  return <span>{result}</span>;
};

export default DateFormatter;
