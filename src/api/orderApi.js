import axiosClient from './axiosClient';

const orderApi = {
    createOrder: (orderRequest) => {
        return axiosClient.post('/order/create', orderRequest);
    },

    chargeOrder: (orderId) => {
        return axiosClient.put(`/order/charge/${orderId}`);
    },

    getOrderByUserId: (userId) => {
        return axiosClient.get(`order/user/${userId}`);
    },

    changeSubscriptionPlan: (userId, newPlanId) => {
        return axiosClient.put(`/order/change-plan/${userId}/${newPlanId}`);
    }
};

export default orderApi;
