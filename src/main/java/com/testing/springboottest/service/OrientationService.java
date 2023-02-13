package com.testing.springboottest.service;

import com.testing.springboottest.util.Orientation;

public class OrientationService {

  private Orientation orientation;

  public OrientationService() {
    this.orientation = Orientation.NORD;
  }

  public Orientation getOrientation() {
    return orientation;
  }

  public void tourner() {

    switch (orientation) {
      case NORD:
        this.orientation = Orientation.EST;
        break;
      case EST:
        this.orientation = Orientation.SUD;
        break;
      case SUD:
        this.orientation = Orientation.OUEST;
        break;
      case OUEST:
        this.orientation = Orientation.NORD;
        break;
      default:
        this.orientation = Orientation.NORD;
        break;
    }}}
