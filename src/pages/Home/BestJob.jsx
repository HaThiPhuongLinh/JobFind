import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faAngleRight,
  faAngleLeft,
  faFilter,
  faAngleDown,
} from "@fortawesome/free-solid-svg-icons";

const filters = [
  {
    "Địa điểm": ["Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng"],
  },
];

const BestJob = () => {
  return (
    <div className="pt-6" style={{ backgroundColor: "#f3f5f7" }}>
      <div className="container mx-auto">
        {/* start: header */}
        <div className="flex justify-between items-center">
          <h1 className="text-primary text-3xl font-bold">Việc làm tốt nhất</h1>
          <div className="flex justify-between items-center">
            <p className="pe-4 underline text-sm cursor-pointer hover:no-underline">
              Xem tất cả
            </p>
            <div className="">
              <FontAwesomeIcon
                icon={faAngleLeft}
                className="me-4 btn-circle text-xl"
              />
              <FontAwesomeIcon
                icon={faAngleRight}
                className="btn-circle text-xl"
              />
            </div>
          </div>
        </div>
        {/* end: header */}

        {/* start: filter */}
        <div className="pt-6 flex justify-between ">
          {/* filter selector */}
          <div className="flex justify-between items-center border border-slate-300 rounded-md px-4 py-2">
            <FontAwesomeIcon icon={faFilter} className="pe-4 text-slate-400" />
            <span className="text-slate-400 pe-4">Lọc theo:</span>
            <span className="text-base pe-12 text-slate-600">Địa điểm</span>
            <FontAwesomeIcon icon={faAngleDown} className="text-slate-600" />
          </div>

          {/* list filter */}
          <div>
            <FontAwesomeIcon
              icon={faAngleLeft}
              className="me-4 btn-circle text-xl"
            />

            <FontAwesomeIcon
              icon={faAngleRight}
              className="btn-circle text-xl"
            />
          </div>
        </div>
        {/* end: filter */}
      </div>
    </div>
  );
};

export default BestJob;
