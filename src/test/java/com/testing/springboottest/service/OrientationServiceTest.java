package com.testing.springboottest.service;

import static org.junit.Assert.*;
import org.junit.Test;
import com.testing.springboottest.util.Orientation;

public class OrientationServiceTest {

  @Test
  public void testPositionInitialeNord() {
    OrientationService orientationService = new OrientationService();
    assertEquals(Orientation.NORD, orientationService.getOrientation());
  }
  
  @Test
  public void testTounerUneFois() {
    OrientationService orientationService = new OrientationService();
    orientationService.tourner();
    assertEquals(Orientation.EST, orientationService.getOrientation());
  }
  
  @Test
  public void testTounerDeuxFois() {
    OrientationService orientationService = new OrientationService();
    orientationService.tourner();
    orientationService.tourner();
    assertEquals(Orientation.SUD, orientationService.getOrientation());
  }
  
  @Test
  public void testTounerTroisFois() {
    OrientationService orientationService = new OrientationService();
    orientationService.tourner();
    orientationService.tourner();
    orientationService.tourner();
    assertEquals(Orientation.OUEST, orientationService.getOrientation());
  }
  
  @Test
  public void testTounerQuatreFois() {
    OrientationService orientationService = new OrientationService();
    orientationService.tourner();
    orientationService.tourner();
    orientationService.tourner();
    orientationService.tourner();
    assertEquals(Orientation.NORD, orientationService.getOrientation());
  }}
