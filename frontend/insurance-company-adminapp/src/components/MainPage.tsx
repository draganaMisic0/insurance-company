import { useEffect, useState } from "react";
import { FaUser } from "react-icons/fa";
import "./MainPage.css";
import "./../variables.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function MainPage() {
  const navigate = useNavigate();
  const [dropdownVisible, setDropdownVisible] = useState(false);
  const [user, setUser] = useState<{
    id: number;
    username: string;
    email: string;
    role: string;
  } | null>(null);

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
        navigate("/admin/auth/login");
      }
    };

    getUsername();
  }, [navigate]);

  return (
    <div className="full-page">
      <div className="heading">
        <div className="left-text">
          Insurance company
          <br />
          <span className="subtext">Administrator app</span>
        </div>
        <div className="user-area">
          <div className="user-info" onClick={handleToggleDropdown}>
            <FaUser size={20} />
            <span className="username">{user?.username || "Loading..."}</span>
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
          <a className="navbar-brand" href="#">
            Navbar
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div className="navbar-nav">
              <a className="nav-link active" aria-current="page" href="#">
                Home
              </a>
              <a className="nav-link" href="#">
                Features
              </a>
              <a className="nav-link" href="#">
                Pricing
              </a>
              <a className="nav-link disabled" aria-disabled="true">
                Disabled
              </a>
            </div>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default MainPage;
