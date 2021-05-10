package models

enum Region(val value: String):
  case UsEast1 extends Region("us-east-1")
  case UsEast2 extends Region("us-east-2")
  case UsWest1 extends Region("us-west-1")
  case UsWest2 extends Region("us-west-2")
  case AfSouth1 extends Region("af-south-1")
  case ApEast1 extends Region("ap-east-1")
  case ApSouth1 extends Region("ap-south-1")
