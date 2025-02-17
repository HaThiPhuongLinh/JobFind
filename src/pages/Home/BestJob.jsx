import { useState, useEffect, useRef } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faAngleRight,
  faAngleLeft,
  faFilter,
  faAngleDown,
} from "@fortawesome/free-solid-svg-icons";

import JobItem from "../../components/ui/JobItem";
import { useSelector, useDispatch } from "react-redux";
import { filterJob } from "../../redux/slices/jobSlice";

const filters = [
  {
    key: "Địa điểm",
    list: ["Tất cả", "Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng"],
  },
  {
    key: "Mức lương",
    list: [
      "Tất cả",
      "Dưới 5 triệu",
      "5 - 10 triệu",
      "10 - 20 triệu",
      "Trên 20 triệu",
    ],
  },
  {
    key: "Kinh nghiệm",
    list: [
      "Tất cả",
      "Chưa có kinh nghiệm",
      "Dưới 1 năm",
      "1 - 2 năm",
      "Trên 2 năm",
    ],
  },
  {
    key: "Ngành nghề",
    list: [
      "Tất cả",
      "IT - Phần mềm",
      "IT - Phần cứng",
      "Kinh doanh",
      "Marketing",
      "IT",
      " Phần cứng",
      "K doanh",
      "Mketing",
      "Dev java",
      " Python",
      "React",
      "Mobile",
    ],
  },
];

const BestJob = () => {
  // Mở model bộ lọc
  const [isOpenFilter, setIsOpenFilter] = useState(false);
  const toggleFilterModal = () => {
    setIsOpenFilter(!isOpenFilter);
  };

  // Đóng model bộ lọc khi click bên ngoài
  const ref = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (ref.current && !ref.current.contains(event.target)) {
        setIsOpenFilter(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  // Theo dõi sự thay đổi bộ lọc
  const [filterSelected, setFilterSelected] = useState(filters[0]);
  const toggleFilter = (filter) => {
    setFilterSelected(filter);
    setFilterItemSelected("Tất cả");
    setIsOpenFilter(false);
  };

  // Lọc danh sách job theo filter đã chọn
  const dispatch = useDispatch();
  const filterJobs = useSelector((state) => state.jobs.filterJobs);

  // Chọn 1 giá trị của bộ lọc
  const [filterItemSelected, setFilterItemSelected] = useState("Tất cả");
  const toggleFilterItem = (item) => {
    setFilterItemSelected(item);
  };

  // Dùng useEffect để lọc danh sách job khi filterItemSelected thay đổi
  useEffect(() => {
    dispatch(filterJob({ key: filterSelected.key, value: filterItemSelected }));
  }, [filterItemSelected, dispatch, filterSelected.key]);

  // Nếu value của bộ lọc quá nhiều thì cho phép cuộn sang trái phải
  const listFilterRef = useRef(null);

  const scrollLeft = () => {
    if (listFilterRef.current) {
      listFilterRef.current.scrollLeft -= 100;
    }
  };

  const scrollRight = () => {
    if (listFilterRef.current) {
      listFilterRef.current.scrollLeft += 100;
    }
  };

  // get job list
  // const jobs = useSelector((state) => state.jobs.jobs);

  return (
    <div className="pt-6 pb-6" style={{ backgroundColor: "#f3f5f7" }}>
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
          <div className="relative" ref={ref}>
            {/* filter selector */}
            <div
              className="flex justify-between items-center border border-slate-300 rounded-md px-4 py-2 cursor-pointer"
              onClick={toggleFilterModal}
            >
              <FontAwesomeIcon
                icon={faFilter}
                className="pe-4 text-slate-400"
              />
              <span className="text-slate-400 pe-4">Lọc theo:</span>
              <div
                className="flex justify-between items-center"
                style={{ width: "170px" }}
              >
                <span className="text-base pe-12 text-slate-600">
                  {filterSelected.key}
                </span>
                <FontAwesomeIcon
                  icon={faAngleDown}
                  className="text-slate-600"
                />
              </div>
            </div>
            {/* end: filter selector */}

            {/* model filter selector */}
            {isOpenFilter && (
              <div className="absolute top-full right-0 rounded-md bg-white shadow-inner border border-slate-300 w-2/3 py-2 mt-0.5">
                {filters.map((filter) => (
                  <div
                    key={filter.key}
                    className="px-2 py-2 cursor-pointer hover:bg-slate-300"
                    onClick={() => toggleFilter(filter)}
                  >
                    <span
                      className={
                        filterSelected.key == filter.key && "text-primary"
                      }
                    >
                      {filter.key}
                    </span>
                  </div>
                ))}
              </div>
            )}
            {/* end: model selector */}
          </div>

          {/* filter item */}
          <div className="flex justify-between items-center">
            <FontAwesomeIcon
              icon={faAngleLeft}
              className="btn-circle text-xl me-4"
              onClick={scrollLeft}
            />

            <div
              className="flex items-center space-x-2 overflow-hidden overflow-x-auto scroll-smooth"
              style={{
                whiteSpace: "nowrap",
                maxWidth: "700px",
                scrollbarWidth: "none",
              }}
              ref={listFilterRef}
            >
              {filterSelected.list.map((v, index) => (
                <div
                  key={index}
                  className={`rounded-full py-2 px-4 mx-1 cursor-pointer border-base ${
                    filterItemSelected === v
                      ? "bg-primary text-white"
                      : "bg-slate-200"
                  }`}
                  onClick={() => toggleFilterItem(v)}
                >
                  <span className="text-sm">{v}</span>
                </div>
              ))}
            </div>

            <FontAwesomeIcon
              icon={faAngleRight}
              className="ms-4 btn-circle text-xl"
              onClick={scrollRight}
            />
          </div>
          {/* end: list filter */}
        </div>
        {/* end: filter */}

        {/* start: list job */}
        <div className={filterJobs.length > 0 && `pt-6 grid grid-cols-3 gap-4`}>
          {filterJobs.length !== 0 ? (
            filterJobs.map((job) => <JobItem key={job.id} job={job} />)
          ) : (
            <p className="text-center block text-2xl text-slate-400 py-6">
              Không tìm thấy job nào huhu
            </p>
          )}
        </div>
        {/* end: list job */}

        {/* pagination */}
        <div
          className="flex justify-between items-center pt-6 mx-auto"
          style={{ width: "200px" }}
        >
          <FontAwesomeIcon
            icon={faAngleLeft}
            className="me-4 btn-circle text-xl"
          />
          <p>
            <span className="text-primary">1</span> /{" "}
            <span className="text-slate-500">84 trang</span>
          </p>
          <FontAwesomeIcon icon={faAngleRight} className="btn-circle text-xl" />
        </div>
      </div>
    </div>
  );
};

export default BestJob;
