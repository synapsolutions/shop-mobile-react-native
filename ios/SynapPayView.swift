//
//  SynapPayView.swift
//  SynapPayShop
//
//  Created by Javier Alberto Perez Valdez on 10/17/20.
//

import Foundation

@objc(SynapPayView)
class SynapPayView: UIView {
  override init(frame: CGRect) {
      super.init(frame:frame)
      
  }
  
  required init?(coder aDecoder: NSCoder) {
      super.init(coder: aDecoder)
      self.backgroundColor = UIColor(red:1.0, green:1.0, blue:1.0, alpha:0.3)
  }
}
