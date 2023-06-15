import React from 'react';
import MyComponent from './components/MyComponent';
import './components/styles.css'

const App: React.FC = () => {
  return (
    <div className="d-flex flex-column justify-content-center w-100 h-100">
      <MyComponent />
    </div>
  );
};

export default App;