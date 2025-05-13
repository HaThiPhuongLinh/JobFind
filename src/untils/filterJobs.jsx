export const filterJobs = (jobs, filter) => {
  let temp = [...jobs];
  // LOCATION
  if (filter.LOCATION && filter.LOCATION !== "Tất cả địa điểm") {
    temp = temp.filter((job) => job.location === filter.LOCATION);
  }

  // WORK TYPE
  if (filter.WORK_TYPE && filter.WORK_TYPE !== "Tất cả") {
    console.log("filter.WORKTYPE", filter.WORK_TYPE);
    temp = temp.filter(
      (job) =>
        job.jobType ===
        (filter.WORK_TYPE === "Toàn thời gian"
          ? "FULLTIME"
          : filter.WORK_TYPE === "Bán thời gian"
          ? "PARTTIME"
          : "")
    );
    console.log("temp", temp);
  }

  // DATE
  if (filter.DATE && filter.DATE !== "Tất cả ngày đăng") {
    console.log("filter.DATE", filter.DATE);
    if (filter.DATE === "Mới nhất") {
      temp.sort((a, b) => new Date(b.postedAt) - new Date(a.postedAt));
    } else if (filter.DATE === "Cũ nhất") {
      temp.sort((a, b) => new Date(a.postedAt) - new Date(b.postedAt));
    } else if (filter.DATE === "Hạn nộp gần nhất") {
      temp.sort((a, b) => new Date(a.deadline) - new Date(b.deadline));
    } else if (filter.DATE === "Hạn nộp xa nhất") {
      temp.sort((a, b) => new Date(b.deadline) - new Date(a.deadline));
    }
    console.log("temp", temp);
  }

  return temp;

  //   return (
  //     jobs
  //       ?.filter((job) => {
  //         // 1. CATEGORY
  //         if (filter.CATEGORY && job.category !== filter.CATEGORY) return false;

  //         // 2. LOCATION
  //         if (filter.LOCATION && job.location !== filter.LOCATION) return false;

  //         // 3. WORKTYPE
  //         if (filter.WORKTYPE && job.workType !== filter.WORKTYPE) return false;

  //         // 4. EXPERIENCE (giả sử filter.EXPERIENCE là chuỗi: "0-1 năm", "1-3 năm", ...)
  //         if (filter.EXPERIENCE) {
  //           const jobExp = parseFloat(job.experience); // giả sử job.experience là số
  //           const [minExp, maxExp] = filter.EXPERIENCE.split("-").map((v) =>
  //             parseFloat(v)
  //           );
  //           if (jobExp < minExp || jobExp > maxExp) return false;
  //         }

  //         // 5. SALARY (giả sử là khoảng như "500-1000", hoặc "Trên 1000")
  //         if (filter.SALARY) {
  //           const jobSalary = parseInt(job.salary); // job.salary là số
  //           if (filter.SALARY.includes("-")) {
  //             const [minSalary, maxSalary] = filter.SALARY.split("-").map((v) =>
  //               parseInt(v)
  //             );
  //             if (jobSalary < minSalary || jobSalary > maxSalary) return false;
  //           } else if (filter.SALARY.includes("Trên")) {
  //             const min = parseInt(filter.SALARY.replace("Trên ", ""));
  //             if (jobSalary <= min) return false;
  //           }
  //         }

  //         // 6. SKILL (giả sử filter.SKILL là 1 chuỗi, job.skills là mảng)
  //         if (filter.SKILL && Array.isArray(job.skills)) {
  //           if (!job.skills.includes(filter.SKILL)) return false;
  //         }

  //         return true;
  //       })
  //       // 7. Sắp xếp theo ngày
  //       .sort((a, b) => {
  //         if (filter.DATE === "Mới nhất") {
  //           return new Date(b.createdAt) - new Date(a.createdAt);
  //         } else if (filter.DATE === "Cũ nhất") {
  //           return new Date(a.createdAt) - new Date(b.createdAt);
  //         }
  //         return 0;
  //       })
  //   );
};
