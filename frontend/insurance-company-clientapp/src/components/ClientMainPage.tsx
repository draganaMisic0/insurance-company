import React, { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate, Link } from "react-router-dom";
import Cookies from "js-cookie";
import "./ClientMainPage.css";
import "./../variables.css";
import { FaUser } from "react-icons/fa";
import InsuranceList from "./InsuranceList";
import ActiveInsurance from "./AciveInsurance";
import Transaction from "./Transaction";
import axios from "axios";

const ClientDashboard: React.FC = () => {
  const navigate = useNavigate();

  const [user, setUser] = useState<{
    id: number;
    username: string;
    email: string;
    role: string;
  } | null>(null);
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const [selectedSection, setSelectedSection] = useState("list-insurance");

  const handleToggleDropdown = () => {
    setDropdownVisible(!dropdownVisible);
  };

  const handleLogout = async () => {
    try {
      await axios.post(
        "https://localhost:8443/logout",
        {},
        { withCredentials: true }
      );

      setUser(null);

      navigate("/client/auth/login");
    } catch (error) {
      console.error("Logout failed", error);
    }
  };

  useEffect(() => {
    const getUsername = async () => {
      try {
        const response = await axios.get("https://localhost:8443/auth/me", {
          withCredentials: true,
        });

        setUser(response.data);
      } catch (error) {
        console.error("Failed to fetch user info", error);
        navigate("/client/auth/login");
      }
    };

    getUsername();
  }, [navigate]);

  return (
    <div>
      {/*{isAuthorized ? (  */}
      <div className="full-page">
        <div className="heading">
          <div className="left-text">
            Insurance company
            <br />
            <span className="subtext">Client app</span>
          </div>
          <div className="user-area">
            <div className="user-info" onClick={handleToggleDropdown}>
              <FaUser size={20} />
              <span className="username">{user?.username || "Loading"}</span>
            </div>
            {dropdownVisible && (
              <div className="dropdown">
                <button onClick={handleLogout}>Logout</button>
              </div>
            )}
          </div>
        </div>

        <nav className="navbar navbar-expand-lg bg-body-tertiary">
          <div className="container-fluid">
            <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
              <div className="navbar-nav">
                <button
                  className={`nav-link btn btn-link ${
                    selectedSection === "list-insurance" ? "active" : ""
                  }`}
                  onClick={() => setSelectedSection("list-insurance")}
                >
                  Insurance List
                </button>
                <button
                  className={`nav-link btn btn-link ${
                    selectedSection === "active-insurance" ? "active" : ""
                  }`}
                  onClick={() => setSelectedSection("active-insurance")}
                >
                  Active Insurance
                </button>
                <button
                  className={`nav-link btn btn-link ${
                    selectedSection === "transaction" ? "active" : ""
                  }`}
                  onClick={() => setSelectedSection("transaction")}
                >
                  Transactions
                </button>
              </div>
            </div>
          </div>
        </nav>

        <div className="container mt-4">
          {selectedSection === "list-insurance" && <InsuranceList />}
          {selectedSection === "active-insurance" && <ActiveInsurance />}
          {selectedSection === "transaction" && <Transaction />}
        </div>
      </div>
      {/** ) : (
        <div>Loading...</div>
      )}
        */}
    </div>
  );
};

export default ClientDashboard;
