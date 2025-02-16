//https://www.topcv.vn/login
import { Link } from "react-router-dom";
import bg_signup from "../../assets/logo_with_bg.jpg";
import LoginForm from "./LoginForm";

const Login = () => {
  return (
    <div className="flex justify-between" style={{ height: "100vh" }}>
      <LoginForm />
      <Link to="/" className="w-2/5 h-full">
        <img src={bg_signup} alt="background signup" className="h-full" />
      </Link>
    </div>
  );
};

export default Login;
