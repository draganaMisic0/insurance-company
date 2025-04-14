import { Navigate } from "react-router-dom";
import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";

function isTokenValid(token: string | undefined): boolean {
  if (!token) return false;

  try {
    const decoded: any = jwtDecode(token);
    const currentTime = Date.now() / 1000;
    return decoded.exp > currentTime;
  } catch (e) {
    return false;
  }
}

const PrivateRoute = ({ children }: { children: JSX.Element }) => {
  const token = Cookies.get("token");

  return isTokenValid(token) ? children : <Navigate to="/auth/login" replace />;
};

export default PrivateRoute;
