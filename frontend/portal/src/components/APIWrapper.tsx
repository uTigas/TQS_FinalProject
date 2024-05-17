
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

    fetchOrganizations : async (origin : string|null = null, destination : string|null = null) => {
      try{
        let originArg
        originArg = "?origin=" + origin
        return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'connections' + (origin ? `?origin=${origin}` : '') + (origin && destination ? `&destination=${destination}` : '') + (!origin && destination ? `?destination=${destination}` : ''),
        { method: 'GET', credentials: 'include' })
        
      } catch (error){
        console.error('Error fetching Organizations', error);
      }
    },


}
export default APIWrapper;