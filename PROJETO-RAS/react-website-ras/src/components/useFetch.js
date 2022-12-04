import {useState, useEffect} from "react";
import axios from 'axios';


function useFetch(url) {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect (() => {
        setLoading(true);
        axios.get(url).then((response) => {
            setData(response.data);
        }).catch((error) => {
            setError(error);
        }).finally(() => {
            setLoading(false);
        });
    }, []);

    return {data, loading, error};
}

export default useFetch;