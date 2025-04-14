// App.tsx
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import ClientMainPage from "./components/ClientMainPage";
import PrivateRoute from "./components/PrivateRoute";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/auth/login" element={<Login />} />
        <Route path="auth/register" element={<Register />} />
        <Route
          path="/main"
          element={
            <PrivateRoute>
              <ClientMainPage />
            </PrivateRoute>
          }
        ></Route>
      </Routes>
    </Router>
  );
};

export default App;
