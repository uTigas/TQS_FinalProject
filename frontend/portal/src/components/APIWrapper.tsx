
const APIWrapper = {
  //backendURI: "/", 
  backendURI: "/", 
  privateAPI: "private/api/v1/",
  adminAPI: "admin/api/v1/",
  authAPI: "auth/",

  fetchUserDetails: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.authAPI + 'user', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching User details', error);
    }
  },

  fetchStationList: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Stations', error);
    }
  },

  addStation: async (stationName: string, stationLines: number) => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'name': stationName, 'numberOfLines': stationLines })
      });
    } catch (error) {
      console.error('Error adding Station', error);
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

  fetchTrainList: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'trains', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Trains', error);
    }
  },

  addTrain: async (type: string, number: number) => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'trains', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'type': type, 'number': number })
      });
    } catch (error) {
      console.error('Error adding Train', error);
    }
  },
}
export default APIWrapper;