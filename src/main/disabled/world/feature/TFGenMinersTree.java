package twilightforest.world.feature;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.block.BlockTFMagicLog;
import twilightforest.block.TFBlocks;
import twilightforest.enums.MagicWoodVariant;
import twilightforest.world.TFWorld;

import java.util.Random;

public class TFGenMinersTree extends TFTreeGenerator {

	public TFGenMinersTree() {
		this(false);
	}

	public TFGenMinersTree(boolean notify) {
		super(notify);
		this.treeState = TFBlocks.magic_log.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.MINE);
		this.branchState = treeState.with(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE);
		this.leafState = TFBlocks.magic_leaves.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.MINE).with(BlockLeaves.CHECK_DECAY, false);
		this.rootState = TFBlocks.root.getDefaultState();
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		if (pos.getY() >= TFWorld.MAXHEIGHT - 12) {
			return false;
		}

		// check soil
		BlockState state = world.getBlockState(pos.down());
		if (!state.getBlock().canSustainPlant(state, world, pos.down(), Direction.UP, source)) {
			return false;
		}

		// 9 block high trunk
		for (int dy = 0; dy < 10; dy++) {
			setBlockAndNotifyAdequately(world, pos.up(dy), branchState);
		}

		// branches with leaf blocks
		putBranchWithLeaves(world, pos.add(0, 9, 1), true);
		putBranchWithLeaves(world, pos.add(0, 9, 2), false);
		putBranchWithLeaves(world, pos.add(0, 8, 3), false);
		putBranchWithLeaves(world, pos.add(0, 7, 4), false);
		putBranchWithLeaves(world, pos.add(0, 6, 5), false);

		putBranchWithLeaves(world, pos.add(0, 9, -1), true);
		putBranchWithLeaves(world, pos.add(0, 9, -2), false);
		putBranchWithLeaves(world, pos.add(0, 8, -3), false);
		putBranchWithLeaves(world, pos.add(0, 7, -4), false);
		putBranchWithLeaves(world, pos.add(0, 6, -5), false);

		// place minewood core
		setBlockAndNotifyAdequately(world, pos.up(), TFBlocks.magic_log_core.getDefaultState().with(BlockTFMagicLog.VARIANT, MagicWoodVariant.MINE));
		world.scheduleUpdate(pos.up(), TFBlocks.magic_log_core, TFBlocks.magic_log_core.tickRate(world));


		// root bulb
		if (TFGenerator.hasAirAround(world, pos.down())) {
			this.setBlockAndNotifyAdequately(world, pos.down(), treeState);
		} else {
			this.setBlockAndNotifyAdequately(world, pos.down(), rootState);
		}

		// roots!
		int numRoots = 3 + rand.nextInt(2);
		double offset = rand.nextDouble();
		for (int b = 0; b < numRoots; b++) {
			buildRoot(world, pos, offset, b);
		}

		return true;
	}


	protected void putBranchWithLeaves(World world, BlockPos pos, boolean bushy) {
		setBlockAndNotifyAdequately(world, pos, branchState);

		for (int lx = -1; lx <= 1; lx++) {
			for (int ly = -1; ly <= 1; ly++) {
				for (int lz = -1; lz <= 1; lz++) {
					if (!bushy && Math.abs(ly) > 0 && Math.abs(lx) > 0) {
						continue;
					}
					TFGenerator.putLeafBlock(this, world, pos.add(lx, ly, lz), leafState);
				}
			}
		}
	}


}
