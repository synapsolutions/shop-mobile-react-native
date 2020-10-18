//
//  SynapPayViewManager.swift
//  SynapPayShop
//
//  Created by Javier Alberto Perez Valdez on 10/17/20.
//

import Foundation

@objc(SynapPayViewManager)
class SynapPayViewManager: RCTViewManager {
  
    var synapPayView = SynapPayView()

    override func view() -> UIView! {
      return self.synapPayView
    }

    @objc
    override static func requiresMainQueueSetup() -> Bool {
      return true;
    }
}
