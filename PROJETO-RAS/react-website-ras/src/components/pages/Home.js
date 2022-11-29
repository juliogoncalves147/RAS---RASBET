import '../../App.css';
import HeroSection from '../HeroSection';
import React from 'react';
import Navbar from '../Navbar';
import Footer from '../Footer'


function Home() {
  return (
    <>
      <Navbar />
      <HeroSection />
      <Footer />
    </>
  );
}


export default Home;