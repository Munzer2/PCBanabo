import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';


// const cpus = {}; 

const Builds = () => {
    // const [cpus , setCpus] = useState({}); 
    // const [rams, setRams ] = useState({});  
    // const [mt, setMotherboards] = useState({}); 
    // const [user, setUser] = useState(null); 
    const [builds, setBuilds] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();
    useEffect(() => {
    // Set a timeout to prevent infinite loading
    const timeout = setTimeout(() => {
      if (isLoading) {
        console.log('Loading timeout reached, setting fallback user');
        setUser({ id: localStorage.getItem('userId') || 'unknown', name: 'User' });
        setIsLoading(false);
      }
    }, 3000); // Reduced to 3 second timeout
    
    // Check if user is logged in
    (async () => {
      try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          console.log('No userId found, redirecting to login');
          clearTimeout(timeout);
          setIsLoading(false);
          return navigate('/login', { replace: true });
        }
        
        console.log('Fetching user data for userId:', userId);
        
        // Try to fetch user data, but don't fail if it doesn't work
        try {
          const res = await api.get(`/users/${userId}`);
          clearTimeout(timeout);
          setUser(res.data);
          setIsLoading(false);
        } catch (apiError) {
          console.log('API call failed, using fallback user data');
          clearTimeout(timeout);
          setUser({ id: userId, name: 'User', email: 'user@example.com' });
          setIsLoading(false);
        }
      } catch (error) {
        console.error('Error in user setup:', error);
        clearTimeout(timeout);
        setUser({ id: 'fallback-user', name: 'User' });
        setIsLoading(false);
      }
    })();
    
    return () => clearTimeout(timeout);
  }, [navigate, isLoading]);
  
  
  useEffect(() => {
    const fetchBuilds = async() => {
        try {
          console.log("here");
            setIsLoading(true);
            const res = await fetch(`/api/shared-builds`);
            if(!res.ok) { 
                throw new Error("Cant do it."); 
            } 

            const data = await res.json(); 
            setBuilds(data);
            console.log("Received data:", data.length, "items");
            console.log(data); 
            

            

            
            setIsLoading(false); // End loading state
        }
        catch( error ) {
            console.error("Error fetching builds:", error);
        }
    };

    fetchBuilds();
  },[]);
  
  useEffect(() => {
    
  },builds); 

  if(isLoading) {
    return (
        <div>Loading...</div>
    )
  }

  return (
    <div>
      {builds.map(b => (
        <div key = {b.id}>
          <div>{b.buildName}</div>
         </div>
      ))}
    </div>
  )
}

export default Builds
