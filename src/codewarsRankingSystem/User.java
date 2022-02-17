package codewarsRankingSystem;

class User {
    public int rank = -8;
    public int progress = 0;
    
    public static void main(String[] args) {
    	User user = new User();
    	user.incProgress(1);
	}

    public void incProgress(int exerciseRank) {
        System.out.println("curr rank: " + this.getRank());
        System.out.println("curr progress: " + this.getProgress());
        System.out.println("exercise: " + exerciseRank);
    	// Validation
    	if (exerciseRank < -8 || exerciseRank > 8 || exerciseRank == 0) {
    		throw new IllegalArgumentException("Invalid rank!");
    	}
    	
        int rankDiff = Math.abs(exerciseRank - this.getRank());
        
        // Adjust if between 0.
        if (exerciseRank > 0 && this.getRank() < 0 || exerciseRank < 0 && this.getRank() > 0) {
        	rankDiff -= 1;
        }
        
        if (rankDiff >= 2 && exerciseRank < rank) {
            return;
        } else if (rankDiff == 1 && exerciseRank < rank) {
            this.addProgress(1);
        } else if (rankDiff == 0) {
            this.addProgress(3);
        } else {
            this.addProgress(10 * rankDiff * rankDiff);
        }
        
        if (this.rank == 8) {
        	this.progress = 0;
        }
        
        System.out.println("curr rank: " + this.getRank());
        System.out.println("curr progress: " + this.getProgress());
        System.out.println("exercise: " + exerciseRank);
        
    }

    public void addProgress(int pointsToAdd) {
        if (pointsToAdd + this.getProgress() < 100) {
            this.setProgress(this.getProgress() + pointsToAdd);
        } else if ((pointsToAdd + this.getProgress()) >= 100) {
            int levelsToRankUp = (this.getProgress() + pointsToAdd) / 100;
            int remainingPoints = (pointsToAdd + this.getProgress()) % 100;
            this.addRank(levelsToRankUp);
            this.setProgress(remainingPoints);
        }
    }
    
    public void setProgress(int points) {
    	this.progress = points;
    }

    public void addRank(int levels) {
    	int previousLevel = this.rank;
        this.rank += levels;
        
        if (previousLevel < 0 && this.rank >= 0) {
        	this.rank += 1;
        }
        	
        if (rank > 8) {
        	this.rank = 8;
        }
    }

    public int getRank() {
        return rank;
    }
    public int getProgress() {
        return progress;
    }
}