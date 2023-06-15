import React from 'react';
import './styles.css'

interface ButtonProps {
  label: string;
  onClick: () => void;
}

const Button: React.FC<ButtonProps> = ({ label, onClick }) => {
  return (
    <button className = 'big-btn' onClick={onClick}>
      {label}
    </button>
  );
};

export default Button;