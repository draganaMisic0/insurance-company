import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  useNavigate,
  useLocation,
} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import ClientMainPage from "./components/ClientMainPage";
import PrivateRoute from "./components/PrivateRoute";
import axios from "./axios";
import VerifyLogin from "./components/VerifyLogin";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (
      location.pathname !== "/client/auth/login" &&
      location.pathname !== "/client/auth/register" &&
      location.pathname !== "/client/auth/verify-code"
    ) {
      const checkAuth = async () => {
        try {
          await axios.get("https://localhost:8443/auth/check", {
            withCredentials: true,
          });
          setIsAuthenticated(true);
          navigate("/client/main");
        } catch (error) {
          setIsAuthenticated(false);
          navigate("/client/auth/login");
        }
      };

      checkAuth();
    }
  }, [navigate, location]);
  return (
    <Routes>
      <Route path="/client/auth/login" element={<Login />} />
      <Route path="/client/auth/register" element={<Register />} />
      <Route
        path="/client/main"
        element={
          <PrivateRoute>
            <ClientMainPage />
          </PrivateRoute>
        }
      />
      <Route path="/client/auth/verify-code" element={<VerifyLogin />} />
    </Routes>
  );
}

export default App;
