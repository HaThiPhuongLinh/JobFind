import SignUpForm from "./SignUpForm";
import bg_signup from "../../assets/logo_with_bg.jpg";
import { Link } from "react-router-dom";

const Signup = () => {
  return (
    <div className="flex justify-between" style={{ height: "100vh" }}>
      <SignUpForm />
      <Link to="/" className="w-2/5 h-full">
        <img src={bg_signup} alt="background signup" className="h-full" />
      </Link>
    </div>
  );
};

export default Signup;
