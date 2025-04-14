import React, { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie"; // Import js-cookie to work with cookies

const ClientDashboard: React.FC = () => {
  const [isAuthorized, setIsAuthorized] = useState<boolean>(false);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("is anything happening");
    // Retrieve token from cookies
    const token = Cookies.get("token");
    console.log(token);

    if (!token) {
      console.log("cookie was not found");
      navigate("/login");
      return;
    }

    try {
      const decoded: any = jwtDecode(token);
      console.log(decoded);
      if (decoded.role && decoded.role[0]?.authority === "ROLE_ADMIN") {
        console.log("role admin");
        setIsAuthorized(true);
      } else {
        console.log("nije admin");
        setIsAuthorized(false);
        navigate("/login");
      }
    } catch (error) {
      console.error("Invalid token", error);
      setIsAuthorized(false);
      navigate("/login");
    }
  }, [navigate]);

  return (
    <div>
      {isAuthorized ? (
        <div>Welcome to the Client Dashboard</div>
      ) : (
        <div>Loading...</div>
      )}
    </div>
  );
};

export default ClientDashboard;
