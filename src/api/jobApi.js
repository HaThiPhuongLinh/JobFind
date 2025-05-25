import axiosClient from "./axiosClient";
import algoliasearch from 'algoliasearch/lite';
const searchClient = algoliasearch('QBHON3L0WI', '7a4ef78c9eb26f9b211100e4a004e256');

const jobApi = {
  getAll: () => {
    const url = '/job/all';
    return axiosClient.get(url);
  },

  create: (jobData) => {
    const url = "/job/create";
    return axiosClient.post(url, jobData);
  },

  pushToAlgolia: () => {
    const url = "/job/pushAlgolia";
    return axiosClient.post(url);
  },

  update: (jobId, jobData) => {
    const url = `/job/update`;
    return axiosClient.put(url, { jobId, ...jobData });
  },

  delete: (jobId) => {
    const url = `/job/delete/${jobId}`;
    return axiosClient.delete(url);
  },

  // search: (keyword, locations, jobCategoryIds) => {
  //   const url = "/job/searchJobs";

  //   // Nếu không có tham số nào thì lấy tất cả job
  //   if (!keyword && !locations && !jobCategoryIds) {
  //     return axiosClient.get(url);
  //   }

  //   const params = {};
  //   if (keyword) params.keyword = keyword;
  //   if (locations && locations.length > 0) {
  //     params.location = locations;
  //   }
  //   if (jobCategoryIds && jobCategoryIds.length > 0)
  //     params.jobCategoryId = jobCategoryIds;
  //   // console.log("params: ", params);

  //   return axiosClient.get(url, { params });
  // },

  search: async (keyword, locations, jobCategoryIds) => {
    const index = searchClient.initIndex('jobs_index');

    const isKeywordEmpty = !keyword || keyword.trim() === '';
    const isLocationsEmpty = !locations || locations.length === 0;
    const isCategoryIdsEmpty = !jobCategoryIds || jobCategoryIds.length === 0;

    let filters = 'isActive:true AND isApproved:true AND isDeleted:false AND expired:false';
    if (!isLocationsEmpty) {
      const locationFilters = locations.map(loc => `location:"${loc}"`).join(' OR ');
      filters += ` AND (${locationFilters})`;
    }
    if (!isCategoryIdsEmpty) {
      const categoryFilters = jobCategoryIds.map(id => `categories.jobCategoryId:${id}`).join(' OR ');
      filters += ` AND (${categoryFilters})`;
    }

    const query = isKeywordEmpty ? '' : keyword;

    const result = await index.search(query, {
      filters,
      hitsPerPage: 100,
      sortFacetValuesBy: 'alpha',
      distinct: true,
    });

    const jobs = result.hits.map(hit => ({
      ...hit,
      category: hit.categories?.[0]?.name || '',
    }));

    return jobs;
  },

  getByCompanyId: (companyId, id) => {
    const url = `/job/company/${companyId}`;
    return axiosClient.get(url, {
      params: {
        id: id,
      },
    });
  },

  getByCategory: (categoryId) => {
    const url = `/job/category/${categoryId}`;
    return axiosClient.get(url);
  },

  getSkillsAndCategories: (jskId) => {
    const url = `/job/${jskId}/skills-and-categories`;
    return axiosClient.get(url);
  },

  getById: (jobId) => {
    const url = `/job/getJobById/${jobId}`;
    return axiosClient.get(url);
  },

  approve: (jobId) => {
    const url = `/job/approve/${jobId}`;
    return axiosClient.put(url);
  },

  reject: (rejectJobRequest) => {
    const url = `/job/reject`;
    return axiosClient.put(url, rejectJobRequest);
  },

  getPosition: () => {
    const url = '/job/jobPosition';
    return axiosClient.get(url);
  },

  getJobPriority: () => {
    const url = '/job/priority';
    return axiosClient.get(url);
  },
};

export default jobApi;
