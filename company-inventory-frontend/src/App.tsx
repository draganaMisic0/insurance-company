import React from "react";
import Login from "./components/Login"; // Importing the Login component
import "./App.css";

const App: React.FC = () => {
  return (
    <div className="login-container">
      <div className="image-section">
        <img
          src="https://via.placeholder.com/600x800"
          alt="login"
          className="login-image"
        />
      </div>
      <Login /> {/* Using the Login component here */}
    </div>
  );
};

export default App;
