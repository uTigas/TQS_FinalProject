import { add } from "ionicons/icons";

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

    fetchStationList : async () => {
        try{
          return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'stations', {method: 'GET', credentials: 'include'});
        } catch (error){
          console.error('Error fetching Stations', error);
        }
    },

    addStation : async (stationName: string, stationLines: number) => {
        try{
          return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'stations', {
            method: 'POST',
            credentials: 'include',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({name: stationName, numberOfLines: stationLines})
          });
        } catch (error){
          console.error('Error adding Station', error);
        }
    }
}
export default APIWrapper;