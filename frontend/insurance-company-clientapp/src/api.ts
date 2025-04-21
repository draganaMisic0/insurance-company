
import axios from "axios";

const api = axios.create({
  baseURL: "https://localhost:8443", 
  withCredentials: true
});

api.interceptors.request.use((config) => {
  
  return config;
});

export default api;
