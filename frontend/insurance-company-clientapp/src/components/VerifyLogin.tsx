import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import api from "../api";
import "./VerifyLogin.css";

const VerifyLogin: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const username = location.state?.username;

  const [code, setCode] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<string>("");

  const handleVerify = async (event: React.FormEvent) => {
    event.preventDefault();

    try {
      const response = await api.post("/client/auth/verify-code", {
        username,
        code,
      });

      const token = response.data;
      Cookies.set("token", token, {
        expires: 1 / 24,
        path: "/",
        secure: true,
        sameSite: "Strict",
      });

      navigate("/client/main");
    } catch (error: any) {
      console.error("Verification error:", error);
      if (error.response?.data?.message) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage("Verification failed. Please try again.");
      }
    }
  };

  if (!username) {
    return <div>Error: Missing login information.</div>;
  }

  return (
    <div className="verify-container">
      <div className="left" />
      <div className="right">
        <div className="login-form">
          <h2>Enter Verification Code</h2>
          {errorMessage && <div className="error-message">{errorMessage}</div>}
          <form onSubmit={handleVerify}>
            <div>
              <label htmlFor="code">Code</label>
              <input
                type="text"
                id="code"
                value={code}
                onChange={(e) => setCode(e.target.value)}
                required
              />
            </div>
            <button type="submit">Verify</button>
          </form>
          <a
            href="/client/auth/login"
            style={{ display: "block", marginTop: "1rem", color: "gray" }}
          >
            Back to login
          </a>
        </div>
      </div>
    </div>
  );
};

export default VerifyLogin;
