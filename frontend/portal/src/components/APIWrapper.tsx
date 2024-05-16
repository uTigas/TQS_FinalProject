
const APIWrapper = {
    backendURI : "http://localhost:8080/",
    privateAPI : "private/api/v1/",
    fetchUserDetails : async () => {
        try{
          return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'user', {method: 'GET', credentials: 'include'});
        } catch (error){
          console.error('Error fetching User details', error);
        }
    },


}
export default APIWrapper;