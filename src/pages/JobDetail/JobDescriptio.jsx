import { Link } from "react-router-dom";

const JobDescription = () => {
  return (
    <div>
      <div>
        <p>Chi tiết tin tuyển dụng</p>
        <button>
          <Link to="#">Gửi tôi việc làm tương tự</Link>
        </button>
      </div>
    </div>
  );
};

export default JobDescription;
