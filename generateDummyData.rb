#!/usr/bin/env ruby
actions = ['selling', 'buying']

def generateRandomCost(base)
  base + (rand(0..base) * (-0.1) ** rand(1..2))
end

for i in 0..1000
  print [actions.sample, generateRandomCost(400)].join(" ") + "\n"
end