package com.ukonnra.wonderland.infrastructure.core

interface Event<ID : Identifier<ID>> {
  val targetId: ID
}
