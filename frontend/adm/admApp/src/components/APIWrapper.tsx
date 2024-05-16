
const APIWrapper = {
    backendURI : "http://localhost:8080/",
    privateAPI : "admin/api/v1/",
    fetchAdminDetails : async () => {
        try{
          return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'admin', {method: 'GET', credentials: 'include'});
        } catch (error){
          console.error('Error fetching Admin details', error);
        }
    },


}
export default APIWrapper;