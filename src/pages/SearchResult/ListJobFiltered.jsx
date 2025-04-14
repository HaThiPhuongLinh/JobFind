import { useSelector } from "react-redux";
import JobItemv2 from "../../components/ui/JobItemv2";

const ListJob = () => {
  const jobs = useSelector((state) => state.jobs.filterJobs);
  // console.log(jobs);

  return (
    <div>
      {jobs.map((job) => (
        <JobItemv2 job={job} key={job.jobId} isApply={false} />
      ))}
    </div>
  );
};

export default ListJob;
