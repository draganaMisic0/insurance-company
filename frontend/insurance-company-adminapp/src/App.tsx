import Login from "./components/Login";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MainPage from "./components/MainPage";
import PrivateRoute from "./components/PrivateRoute";
import VerifyLogin from "./components/VeirfyLogin";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/admin/auth/login" element={<Login />} />
        <Route
          path="/admin/main"
          element={
            <PrivateRoute>
              <MainPage />
            </PrivateRoute>
          }
        />
        <Route path="/admin/auth/verify-code" element={<VerifyLogin />} />
      </Routes>
    </Router>
  );
}

export default App;
