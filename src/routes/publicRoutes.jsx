import Home from "../pages/Home";
import CompanyDetail from "../pages/CompanyDetail";
import CompanyList from "../pages/CompanyList";
import JobDetail from "../pages/JobDetail";
import SearchResult from "../pages/SearchResult";
import TemplateCV from "../pages/TemplateCV";
<<<<<<< HEAD
import LoginAndRegister from "../pages/LoginAndRegister";
=======
import Login from "../pages/Login";
import Signup from "../pages/Signup";
import RecruiterOverview from "../pagesRecruiter/RecruiterOverview";
>>>>>>> eebf06313744317fd7198f098be6ca0b826d195b

const publicRoutes = [
  { path: "/", element: <Home /> },
  { path: "/company-detail/:id", element: <CompanyDetail /> },
  { path: "/company-list", element: <CompanyList /> },
  { path: "/job-detail/:id", element: <JobDetail /> },
  { path: "/search", element: <SearchResult /> },
  { path: "/template-cv", element: <TemplateCV /> },
<<<<<<< HEAD
  { path: "/login", element: <LoginAndRegister /> },
  { path: "/register", element: <LoginAndRegister /> },
=======
  { path: "/login", element: <Login /> },
  { path: "/signup", element: <Signup /> },
  { path: "/overview", element: <RecruiterOverview /> },
>>>>>>> eebf06313744317fd7198f098be6ca0b826d195b
];

export default publicRoutes;
