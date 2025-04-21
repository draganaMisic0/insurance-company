import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";
import "./Login.css";

function Login() {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errorMessage, setErrorMessage] = useState<React.ReactNode>("");
  const navigate = useNavigate();

  const handleLogin = async (event: React.FormEvent) => {
    event.preventDefault();

    const payload = { username, password };

    try {
      await api.post("/admin/auth/login", payload);
      navigate("/admin/auth/verify-code", { state: { username } });
    } catch (error: any) {
      console.error("Login error:", error);

      if (error.response?.status === 403) {
        console.log("enters status 403");
        setErrorMessage(
          <>
            You do not have permission to access the admin app. <br />
            <a
              href="https://localhost:5174/client/auth/login"
              style={{ color: "blue", textDecoration: "underline" }}
            >
              Go to client app
            </a>
          </>
        );
      } else if (error.response?.data?.message) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage("Login failed. Please try again.");
      }
    }
  };

  return (
    <div className="login-container">
      <div className="left"></div>
      <div className="right">
        <div className="login-form">
          <h2>Login</h2>
          {errorMessage && <div className="error-message">{errorMessage}</div>}
          <form onSubmit={handleLogin}>
            <div>
              <label htmlFor="username">Username</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div>
              <label htmlFor="password">Password</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit">Login</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;
