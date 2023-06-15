import React from 'react';
import './styles.css'

interface InputBoxProps {
  label: string;
  value: string;
  onChange: (value: string) => void;
}

const InputBox: React.FC<InputBoxProps> = ({ label, value, onChange }) => {
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onChange(event.target.value);
  };

  return (
    <div>
      <label className = "searchLabels">{label}</label>
      <input type="text" value={value} onChange={handleChange} />
    </div>
  );
};

export default InputBox;
