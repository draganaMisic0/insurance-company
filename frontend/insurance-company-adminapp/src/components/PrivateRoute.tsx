import { useState, useEffect } from "react";
import { Navigate } from "react-router-dom";
import axios from "./../axios"; // Ensure axios is set up to handle credentials (withCredentials)

const PrivateRoute = ({ children }: { children: JSX.Element }) => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

  useEffect(() => {
    const checkAuth = async () => {
      try {
        // Make a request to the backend to check authentication
        await axios.get("https://localhost:8443/auth/check", {
          withCredentials: true,
        });
        setIsAuthenticated(true); // Set to true if authenticated
      } catch (error) {
        setIsAuthenticated(false); // Set to false if not authenticated
      }
    };

    checkAuth();
  }, []);

  // Loading state while checking authentication
  if (isAuthenticated === null) {
    return <div>Loading...</div>;
  }

  return isAuthenticated ? (
    children
  ) : (
    <Navigate to="/admin/auth/login" replace />
  );
};

export default PrivateRoute;
